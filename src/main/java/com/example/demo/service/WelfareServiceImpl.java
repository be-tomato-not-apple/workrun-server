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
import com.example.demo.web.dto.CursorPageResponse;
import com.example.demo.web.dto.WelfareSummaryDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                .id(welfare.getId())
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
    public CursorPageResponse<WelfareSummaryDTO> getWelfares(Long cursor, int size) {
        log.info("복지정책 목록 조회 (Cursor 방식) - cursor: {}, size: {}", cursor, size);

        // size + 1로 조회해서 다음 페이지 존재 여부 확인
        Pageable pageable = PageRequest.of(0, size + 1);
        List<Welfare> welfares = welfareRepository.findWithCursor(cursor, pageable);

        // 다음 페이지 존재 여부 확인
        boolean hasNext = welfares.size() > size;
        if (hasNext) {
            welfares.remove(welfares.size() - 1); // 마지막 요소 제거
        }

        // 다음 커서 설정
        Long nextCursor = hasNext && !welfares.isEmpty()
                ? welfares.get(welfares.size() - 1).getId()
                : null;

        List<WelfareSummaryDTO> content = welfares.stream()
                .map(this::convertToSummaryDTO)
                .collect(Collectors.toList());

        return CursorPageResponse.of(content, nextCursor, hasNext);
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
