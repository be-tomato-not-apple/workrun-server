package com.example.demo.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.example.demo.apiPayload.code.error.CommonErrorCode;
import com.example.demo.apiPayload.exception.GeneralException;
import com.example.demo.domain.Welfare;
import com.example.demo.domain.mapping.WelfareHomeStatus;
import com.example.demo.domain.mapping.WelfareInterestTopic;
import com.example.demo.dto.welfareDetailDTO;
import com.example.demo.repository.WelfareHomeStatusRepository;
import com.example.demo.repository.WelfareInterestTopicRepository;
import com.example.demo.repository.WelfareRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WelfareServiceImpl implements WelfareService{

	private final WelfareRepository welfareRepository;
	private final WelfareHomeStatusRepository welfareHomeStatusRepository;
	private final WelfareInterestTopicRepository welfareInterestTopicRepository;

	@Override
	@Transactional
	public welfareDetailDTO getDetailById(Long id){
		Welfare welfare = welfareRepository.findById(id).orElseThrow(
			() -> new GeneralException(CommonErrorCode.NOT_EXIST_WELFARE)
		);

		List<WelfareHomeStatus> homeList = welfareHomeStatusRepository.findByWelfareId(id);
		List<WelfareInterestTopic> topicList = welfareInterestTopicRepository.findByWelfareId(id);

		List<String> homeStatusList = homeList.stream()
			.map(whs -> whs.getHomeStatus().getContent())
			.filter(Objects::nonNull).distinct()
			.toList();

		List<String> interestTopicList = topicList.stream()
			.map(interest -> interest.getInterestTopic().getContent())
			.filter(Objects::nonNull).distinct()
			.toList();

		return new welfareDetailDTO().builder()
			.center(welfare.getCenter())
			.serviceName(welfare.getServiceName())
			.content(welfare.getContent())
			.url(welfare.getUrl())
			.target(welfare.getTarget())
			.applyMethod(welfare.getApplyMethod())
			.needDocument(welfare.getNeedDocument())
			.homeStatusList(homeStatusList)
			.topicList(interestTopicList)
			.build();
	}

}
