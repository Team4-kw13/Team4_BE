package org.team4.hanzip.domain.contract.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.team4.hanzip.domain.contract.document.Contract;
import org.team4.hanzip.domain.contract.dto.request.ContractWriteDto;
import org.team4.hanzip.domain.contract.dto.response.ContractDetailDto;
import org.team4.hanzip.domain.contract.dto.response.ContractIdDto;
import org.team4.hanzip.domain.contract.dto.response.ContractListDto;
import org.team4.hanzip.domain.contract.repository.ContractRepository;
import org.team4.hanzip.domain.contract.util.ImageUploader;
import org.team4.hanzip.domain.member.repository.MemberRepository;
import org.team4.hanzip.global.exception.contract.ContractNotFoundException;
import org.team4.hanzip.global.exception.member.MemberNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContractService {
	private final MemberRepository memberRepository;
	private final ContractRepository contractRepository;
	private final ImageUploader imageUploader;

	public ContractIdDto createContract(final long memberId, final ContractWriteDto contractWriteDto) {
		if(!memberRepository.existsById(memberId)){
			throw new MemberNotFoundException();
		}

		return ContractIdDto.of(contractRepository.save(contractWriteDto.toDocument(memberId)).getId());
	}

	public ContractListDto getContractsList(final long memberId, final int page) {
		Pageable pageable = PageRequest.of(page, 10);

		Slice<Contract> slice = contractRepository.findByMemberIdOrderByCreatedDateDesc(memberId, pageable);

		return ContractListDto.from(slice);
	}

	public ContractDetailDto getContractDetail(final long memberId, final String contractId) {
		Contract contract = contractRepository.findByMemberIdAndId(memberId, contractId)
				.orElseThrow(ContractNotFoundException::new);

		return ContractDetailDto.from(contract);
	}

	public void uploadContractImages(final long memberId, final String contractId, final List<MultipartFile> images) {
		List<String> urls = imageUploader.upload(memberId, contractId, images);

		Contract contract = contractRepository.findByMemberIdAndId(memberId, contractId).orElseThrow(ContractNotFoundException::new);

		contract.updateImages(urls);

		contractRepository.save(contract);
	}
}
