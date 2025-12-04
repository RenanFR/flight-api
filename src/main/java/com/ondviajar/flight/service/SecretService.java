package com.ondviajar.flight.service;

import com.google.cloud.secretmanager.v1.AccessSecretVersionRequest;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import org.springframework.stereotype.Service;

@Service
public class SecretService {

	public String getAviationStackKey() {
		try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {

			String projectId = System.getenv("GCP_PROJECT");
			String secretName = String.format("projects/%s/secrets/aviationstack_api_key/versions/latest", projectId);

			var request = AccessSecretVersionRequest.newBuilder().setName(secretName).build();

			String rawKey = client.accessSecretVersion(request).getPayload().getData().toStringUtf8();

			return sanitize(rawKey);

		} catch (Exception e) {
			throw new RuntimeException("Failed to load AviationStack API key", e);
		}
	}

	private String sanitize(String key) {
		return key.replace("\r", "").replace("\n", "").trim();
	}
}
