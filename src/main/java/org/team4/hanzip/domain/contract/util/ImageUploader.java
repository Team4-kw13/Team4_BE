package org.team4.hanzip.domain.contract.util;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.team4.hanzip.global.exception.contract.FileExtensionNotFoundException;
import org.team4.hanzip.global.exception.contract.FileNotFoundException;
import org.team4.hanzip.global.exception.contract.InvalidFileExtensionException;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class ImageUploader {
	private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");
	private final S3Client s3Client;

	@Value("${aws.s3.bucket-name}")
	private String bucketName;

	// https://tao-tech.tistory.com/27

	public List<String> upload(final long memberId, final String contractId, final List<MultipartFile> images) {
		validateFiles(images);

		return IntStream.range(0, images.size())
				.mapToObj(idx -> {
					String filenameToUpload = memberId + "-" + contractId + "-" + idx;
					return uploadToS3(filenameToUpload, images.get(idx));
				})
				.toList();
	}

	private void validateFiles(final List<MultipartFile> images) {
		images.forEach(image -> {
			String fileName = image.getOriginalFilename();

			// 파일 존재 유무 검증
			if (fileName == null || fileName.isEmpty()) {
				throw new FileNotFoundException();
			}

			// 파일 확장자 존재 유무 검증
			if (fileName.lastIndexOf(".") == -1) {
				throw new FileExtensionNotFoundException();
			}

			if (!ALLOWED_EXTENSIONS.contains(fileName.substring(fileName.lastIndexOf(".") + 1))) {
				throw new InvalidFileExtensionException();
			}
		});
	}

	private String uploadToS3(final String filenameToUpload, MultipartFile image) {

		String extension = Objects.requireNonNull(image.getOriginalFilename())
				.substring(image.getOriginalFilename().lastIndexOf(".") + 1);

		try (InputStream inputStream = image.getInputStream()) {
			PutObjectRequest putObjectRequest = PutObjectRequest.builder()
					.bucket(bucketName)
					.key(filenameToUpload)
					.acl(ObjectCannedACL.PUBLIC_READ)
					.contentType("image/" + extension)
					.contentLength(image.getSize())
					.build();
			s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, image.getSize()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return s3Client.utilities()
				.getUrl(url -> url.bucket(bucketName).key(filenameToUpload)).toString();
	}
}
