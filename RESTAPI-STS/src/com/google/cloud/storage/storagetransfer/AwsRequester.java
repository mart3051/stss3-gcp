package com.google.cloud.storage.storagetransfer;

import com.google.api.services.storagetransfer.v1.Storagetransfer;
import com.google.api.services.storagetransfer.v1.model.AwsAccessKey;
import com.google.api.services.storagetransfer.v1.model.AwsS3Data;
import com.google.api.services.storagetransfer.v1.model.Date;
import com.google.api.services.storagetransfer.v1.model.GcsData;
import com.google.api.services.storagetransfer.v1.model.Schedule;
import com.google.api.services.storagetransfer.v1.model.TimeOfDay;
import com.google.api.services.storagetransfer.v1.model.TransferJob;
import com.google.api.services.storagetransfer.v1.model.TransferSpec;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Creates a one-off transfer job from Amazon S3 to Google Cloud Storage.
 */
public final class AwsRequester {
  /**
   * Creates and executes a request for a TransferJob from Amazon S3 to Cloud Storage.
   *
   * <p>The {@code startDate} and {@code startTime} parameters should be set according to the UTC
   * Time Zone. See:
   * https://developers.google.com/resources/api-libraries/documentation/storagetransfer/v1/java/latest/com/google/api/services/storagetransfer/v1/model/Schedule.html#getStartTimeOfDay()
   *
   * @return the response TransferJob if the request is successful
   * @throws InstantiationException
   *           if instantiation fails when building the TransferJob
   * @throws IllegalAccessException
   *           if an illegal access occurs when building the TransferJob
   * @throws IOException
   *           if the client failed to complete the request
   */
  public static TransferJob createAwsTransferJob(
      String projectId,
      String jobDescription,
      String awsSourceBucket,
      String gcsSinkBucket,
      String startDate,
      String startTime,
      String awsAccessKeyId,
      String awsSecretAccessKey)
      throws InstantiationException, IllegalAccessException, IOException {
    Date date = TransferJobUtils.createDate(startDate);
    TimeOfDay time = TransferJobUtils.createTimeOfDay(startTime);
    TransferJob transferJob =
        new TransferJob()
            .setDescription(jobDescription)
            .setProjectId(projectId)
            .setTransferSpec(
                new TransferSpec()
                    .setAwsS3DataSource(
                        new AwsS3Data()
                            .setBucketName(awsSourceBucket)
                            .setAwsAccessKey(
                                new AwsAccessKey()
                                    .setAccessKeyId(awsAccessKeyId)
                                    .setSecretAccessKey(awsSecretAccessKey)))
                    .setGcsDataSink(new GcsData().setBucketName(gcsSinkBucket)))
            .setSchedule(
                new Schedule()
                    .setScheduleStartDate(date)
                    .setScheduleEndDate(date)
                    .setStartTimeOfDay(time))
            .setStatus("ENABLED");

    Storagetransfer client = TransferClientCreator.createStorageTransferClient();
    return client.transferJobs().create(transferJob).execute();
  }

  public static void run(PrintStream out,String date,String time)
      throws InstantiationException, IllegalAccessException, IOException {
    String projectId ="datamovementp1" ;//TransferJobUtils.getPropertyOrFail("projectId");
    String jobDescription = "Sample-S3-CloudStorage-Job";//TransferJobUtils.getPropertyOrFail("jobDescription");
    String awsSourceBucket = "datamovement";//TransferJobUtils.getPropertyOrFail("awsSourceBucket");
    String gcsSinkBucket = "gcpsinkbucket";//TransferJobUtils.getPropertyOrFail("gcsSinkBucket");
    String startDate = date;//TransferJobUtils.getPropertyOrFail("2019-05-15");
    String startTime = time;//TransferJobUtils.getPropertyOrFail("13:52:00.000000000");
    String awsAccessKeyId = "";//TransferJobUtils.getEnvOrFail("AWS_ACCESS_KEY_ID");
    String awsSecretAccessKey ="" ;//TransferJobUtils.getEnvOrFail("AWS_SECRET_ACCESS_KEY");

    TransferJob responseT =
        createAwsTransferJob(
            projectId,
            jobDescription,
            awsSourceBucket,
            gcsSinkBucket,
            startDate,
            startTime,
            awsAccessKeyId,
            awsSecretAccessKey);
    out.println("Return transferJob: " + responseT.toPrettyString());
  }

  /**
   * Output the contents of a successfully created TransferJob.
   */
  public static void main(String[] args) {
    try {
      if(args.length==2)
      {
      run(System.out, args[0], args[1]);
      }
      else 
    	  System.out.println("Invalid no of arguments");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
