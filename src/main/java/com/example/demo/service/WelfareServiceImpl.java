package com.example.demo.service;

import com.example.demo.apiPayload.code.error.CommonErrorCode;
import com.example.demo.apiPayload.exception.GeneralException;
import com.example.demo.domain.Welfare;
import com.example.demo.domain.mapping.WelfareHomeStatus;
import com.example.demo.domain.mapping.WelfareInterestTopic;
import com.example.demo.dto.welfareDetailDTO;
import com.example.demo.repository.WelfareHomeStatusRepository;
import com.example.demo.repository.WelfareInterestTopicRepository;
import com.example.demo.repository.WelfareRepository;
import com.example.demo.web.dto.WelfareSummaryDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WelfareServiceImpl implements WelfareService {

    private final WelfareRepository welfareRepository;
    private final WelfareHomeStatusRepository welfareHomeStatusRepository;
    private final WelfareInterestTopicRepository welfareInterestTopicRepository;

    @Override
    @Transactional
    public welfareDetailDTO getDetailById(Long id) {
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
                .id(welfare.getId())  // ID 추가
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

    @Override
    public Page<WelfareSummaryDTO> getAllWelfares(int page, int size) {
        log.info("복지정책 전체 목록 조회 (페이징) - page: {}, size: {}", page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Welfare> welfarePage = welfareRepository.findAllWithDetails(pageable);

        return welfarePage.map(this::convertToSummaryDTO);
    }

    @Override
    public List<WelfareSummaryDTO> getAllWelfares() {
        log.info("복지정책 전체 목록 조회 (전체)");

        List<Welfare> welfares = welfareRepository.findAllWithDetails();

        return welfares.stream()
                .map(this::convertToSummaryDTO)
                .collect(Collectors.toList());
    }

    private WelfareSummaryDTO convertToSummaryDTO(Welfare welfare) {
        List<String> homeStatusList = welfare.getHomeStatusList().stream()
                .map(whs -> whs.getHomeStatus().getContent())
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        List<String> topicList = welfare.getInterestList().stream()
                .map(wit -> wit.getInterestTopic().getContent())
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        return WelfareSummaryDTO.builder()
                .id(welfare.getId())
                .center(welfare.getCenter())
                .serviceName(welfare.getServiceName())
                .content(welfare.getContent())
                .target(welfare.getTarget())
                .homeStatusList(homeStatusList)
                .topicList(topicList)
                .build();
    }
}
