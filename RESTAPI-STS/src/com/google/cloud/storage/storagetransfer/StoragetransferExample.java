package com.google.cloud.storage.storagetransfer;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.storagetransfer.v1.Storagetransfer;
import com.google.api.services.storagetransfer.v1.model.TransferJob;
import com.google.api.services.storagetransfer.v1.model.UpdateTransferJobRequest;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

public class StoragetransferExample {
  public static void main(String args[]) throws IOException, GeneralSecurityException {
    // The job to get.
    // Required.
    String jobName = "transferJobs/840838214138261919"; // TODO: Update placeholder value.

    Storagetransfer storagetransferService = createStoragetransferService();
    Storagetransfer.TransferJobs.Get request = storagetransferService.transferJobs().get(jobName);
    request.setProjectId("pubsubproj-240606");
    
    //System.out.println();
    TransferJob response = request.execute();
    //Storagetransfer.TransferJobs.Patch patch=storagetransferService.transferJobs().patch("transferJobs/840838214138261919",upd );
    // TODO: Change code below to process the `response` object:
    System.out.println(response);
  }

  public static Storagetransfer createStoragetransferService()
      throws IOException, GeneralSecurityException {
    HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    GoogleCredential credential = GoogleCredential.getApplicationDefault();
    if (credential.createScopedRequired()) {
      credential =
          credential.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
    }
    System.out.println("Credential"+credential.toString());

    return new Storagetransfer.Builder(httpTransport, jsonFactory, credential)
        .setApplicationName("pubsubproj-240606")
        .build();
  }
}