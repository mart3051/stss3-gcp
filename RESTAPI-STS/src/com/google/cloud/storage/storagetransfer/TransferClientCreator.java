package com.google.cloud.storage.storagetransfer;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.storagetransfer.v1.Storagetransfer;
import com.google.api.services.storagetransfer.v1.StoragetransferScopes;
import com.google.common.base.Preconditions;
import java.io.IOException;

/**
 * Create a client to make calls to Storage Transfer API.
 */
public final class TransferClientCreator {

  /**
   * Create a Storage Transfer client using application default credentials and other default
   * settings.
   *
   * @return a Storage Transfer client
   * @throws IOException
   *           there was an error obtaining application default credentials
   */
  public static Storagetransfer createStorageTransferClient() throws IOException {
    HttpTransport httpTransport = Utils.getDefaultTransport();
    JsonFactory jsonFactory = Utils.getDefaultJsonFactory();
    GoogleCredential credential =
        GoogleCredential.getApplicationDefault(httpTransport, jsonFactory);
    return createStorageTransferClient(httpTransport, jsonFactory, credential);
  }

  /**
   * Create a Storage Transfer client using user-supplied credentials and other settings.
   *
   * @param httpTransport
   *          a user-supplied HttpTransport
   * @param jsonFactory
   *          a user-supplied JsonFactory
   * @param credential
   *          a user-supplied Google credential
   * @return a Storage Transfer client
   */
  public static Storagetransfer createStorageTransferClient(
      HttpTransport httpTransport, JsonFactory jsonFactory, GoogleCredential credential) {
    Preconditions.checkNotNull(httpTransport);
    Preconditions.checkNotNull(jsonFactory);
    Preconditions.checkNotNull(credential);

    // In some cases, you need to add the scope explicitly.
    if (credential.createScopedRequired()) {
      credential = credential.createScoped(StoragetransferScopes.all());
    }
    // Please use custom HttpRequestInitializer for automatic
    // retry upon failures. We provide a simple reference
    // implementation in the "Retry Handling" section.
    HttpRequestInitializer initializer = new RetryHttpInitializerWrapper(credential);
    return new Storagetransfer.Builder(httpTransport, jsonFactory, initializer)
        .setApplicationName("storagetransfer-sample")
        .build();
  }
}