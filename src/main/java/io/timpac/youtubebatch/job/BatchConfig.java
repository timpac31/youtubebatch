package io.timpac.youtubebatch.job;

import io.timpac.youtubebatch.domain.YoutubeApi;
import io.timpac.youtubebatch.domain.YoutubeItem;
import io.timpac.youtubebatch.domain.YoutubeWriteItem;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final RestTemplateBuilder restTemplateBuilder;
    private final YoutubeApi youtubeApi;
    private final DataSource dataSource;

    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                       RestTemplateBuilder restTemplateBuilder, YoutubeApi youtubeApi,
                       DataSource dataSource) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.restTemplateBuilder = restTemplateBuilder;
        this.youtubeApi = youtubeApi;
        this.dataSource = dataSource;
    }

    @Bean
    public Job youtubeCollectJob() {
        return new JobBuilder("youtubeCollectJob", jobRepository)
                .start(youtubeCollectStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step youtubeCollectStep() {
        return new StepBuilder("youtubeCollectStep", jobRepository)
                .<YoutubeItem, YoutubeWriteItem>chunk(30, transactionManager)
                .reader(youtubeApiReader())
                .processor(youtubueProcessor())
                .writer(youtubeWriter())
                .build();
    }

    @Bean
    public ItemReader<? extends YoutubeItem> youtubeApiReader() {
        return new YoutubeApiReader(restTemplateBuilder, youtubeApi);
    }

    @Bean
    public ItemProcessor<? super YoutubeItem,? extends YoutubeWriteItem> youtubueProcessor() {
        return new YoutubeMapProcessor();
    }

    @Bean
    public ItemWriter<? super YoutubeWriteItem> youtubeWriter() {
        String sql = "INSERT INTO YOUTUBE_LIST (PUBLISHED_AT, CHANNEL_ID, VIDEO_ID, TITLE, DESCRIPTION, CHANNEL_TITLE, THUMBNAIL_URL) " +
                "VALUES (:publishedAt, :channelId, :videoId, :title, :description, :channelTitle, :thumbnailUrl)";

        return new JdbcBatchItemWriterBuilder<YoutubeWriteItem>()
                .dataSource(dataSource)
                .sql(sql)
                .beanMapped()
                .build();
    }

}
