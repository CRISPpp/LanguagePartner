package com.languagePartner.util;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;

public class FileUploader {
    public void upload() throws Exception{
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("http://www.crisp.tk:11000")
                            .credentials("crisp", "111")
                            .build();

            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket("crisp").build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("crisp").build());
            } else {
                System.out.println("Bucket 'crisp' already exists.");
            }

            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("crisp")
                            .object("d334cb638497d4e2265ca8dd88ab2a8.jpg")
                            .filename("C:\\Users\\BACKPACKER\\Desktop\\d334cb638497d4e2265ca8dd88ab2a8.jpg")
                            .build());
            System.out.println(
                    "'C:\\Users\\BACKPACKER\\Desktop\\d334cb638497d4e2265ca8dd88ab2a8.jpg' is successfully uploaded as "
                            + "object 'd334cb638497d4e2265ca8dd88ab2a8.jpg' to bucket 'crisp'.");
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }
    }
}
