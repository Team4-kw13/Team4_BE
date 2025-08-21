package org.team4.hanzip.domain.contract.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.team4.hanzip.domain.contract.dto.request.ContractWriteDto;
import org.team4.hanzip.domain.contract.dto.response.ContractDetailDto;
import org.team4.hanzip.domain.contract.dto.response.ContractIdDto;
import org.team4.hanzip.domain.contract.dto.response.ContractListDto;
import org.team4.hanzip.domain.contract.service.ContractService;
import org.team4.hanzip.global.api.ApiResponse;
import org.team4.hanzip.global.api.code.contract.SuccessCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/contracts")
public class ContractController {
	private final ContractService contractService;

	@PostMapping
	public ResponseEntity<ApiResponse<ContractIdDto>> createContract(
			@AuthenticationPrincipal final long memberId,
			@RequestBody final ContractWriteDto contractWriteDto
	) {
		return ResponseEntity.status(SuccessCode.CREATED.getStatus())
				.body(ApiResponse.success(SuccessCode.CREATED,
						contractService.createContract(memberId, contractWriteDto)));
	}

	@GetMapping(path = "/{page}")
	public ResponseEntity<ApiResponse<ContractListDto>> getContractsList(
			@AuthenticationPrincipal final long memberId,
			@PathVariable(name = "page") final int page
	) {
		return ResponseEntity.status(SuccessCode.OK.getStatus())
				.body(ApiResponse.success(SuccessCode.OK, contractService.getContractsList(memberId, page)));
	}

	@GetMapping(path = "/detail/{contractId}")
	public ResponseEntity<ApiResponse<ContractDetailDto>> getContractDetail(
			@AuthenticationPrincipal final long memberId,
			@PathVariable(name = "contractId") final String contractId
	) {
		return ResponseEntity.status(SuccessCode.OK.getStatus())
				.body(ApiResponse.success(SuccessCode.OK, contractService.getContractDetail(memberId, contractId)));
	}

	@PostMapping(path = "/image/{contractId}")
	public ResponseEntity<ApiResponse<Void>> uploadImages(
			@AuthenticationPrincipal final long memberId,
			@PathVariable(name = "contractId") final String contractId,
			@RequestPart(name = "image") final List<MultipartFile> images
	) {
		contractService.uploadContractImages(memberId, contractId, images);

		return ResponseEntity.status(SuccessCode.CREATED.getStatus())
				.body(ApiResponse.success(SuccessCode.CREATED));
	}
}
