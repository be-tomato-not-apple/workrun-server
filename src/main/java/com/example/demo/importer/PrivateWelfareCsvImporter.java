package com.example.demo.importer;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.demo.domain.Welfare;
import com.example.demo.domain.mapping.WelfareHomeStatus;
import com.example.demo.domain.mapping.WelfareInterestTopic;
import com.example.demo.repository.*;
import com.example.demo.domain.HomeStatus;
import com.example.demo.domain.InterestTopic;

import lombok.RequiredArgsConstructor;
import com.opencsv.CSVReader;

@Component
@Order(2)
@RequiredArgsConstructor
public class PrivateWelfareCsvImporter implements CommandLineRunner {

	private final WelfareRepository welfareRepository;
	private final HomeStatusRepository homeStatusRepository;
	private final WelfareHomeStatusRepository welfareHomeStatusRepository;
	private final WelfareInterestTopicRepository welfareInterestTopicRepository;
	private final InterestTopicRepository interestTopicRepository;

	@Override
	public void run(String... args) throws Exception {
		if(welfareRepository.count() > 0){
			return;
		} else{
			loadPrivateWelfareFromCsv();
			loadWelfareHomeStatusMapping();
			loadWelfareInterestTopicMapping();
		}

	}

	private void loadPrivateWelfareFromCsv() throws Exception {
		try (CSVReader reader = new CSVReader(
			new InputStreamReader(getClass().getResourceAsStream("/private_welfare.csv"), StandardCharsets.UTF_8))) {

			String[] tokens;
			reader.readNext(); // skip header

			while ((tokens = reader.readNext()) != null) {
				Welfare w = new Welfare();
				w.setCenter(tokens[1]);
				w.setServiceName(tokens[2]);
				w.setContent(tokens[3]);
				w.setUrl(tokens[4]);
				w.setTarget(tokens[5].isEmpty() ? null : tokens[5]);
				w.setApplyMethod(tokens[6].isEmpty() ? null : tokens[6]);
				w.setNeedDocument(tokens[7].isEmpty() ? null : tokens[7]);

				welfareRepository.save(w);
			}
		}
	}

	private void loadWelfareHomeStatusMapping() throws Exception {
		try (CSVReader reader = new CSVReader(
			new InputStreamReader(getClass().getResourceAsStream("/central_welfare_home.csv"), StandardCharsets.UTF_8))) {

			reader.readNext(); // skip header
			String[] tokens;
			while ((tokens = reader.readNext()) != null) {
				WelfareHomeStatus wh = new WelfareHomeStatus();
				Welfare w = welfareRepository.findById(Long.parseLong(tokens[0])).get();
				HomeStatus h = homeStatusRepository.findById(Integer.parseInt(tokens[1])).get();
				wh.setWelfare(w);
				wh.setHomeStatus(h);
				welfareHomeStatusRepository.save(wh);
			}
		}
	}

	private void loadWelfareInterestTopicMapping() throws Exception {
		try (CSVReader reader = new CSVReader(
			new InputStreamReader(getClass().getResourceAsStream("/central_welfare_topic.csv"), StandardCharsets.UTF_8))) {

			reader.readNext(); // skip header
			String[] tokens;
			while ((tokens = reader.readNext()) != null) {
				WelfareInterestTopic wt = new WelfareInterestTopic();
				Welfare w = welfareRepository.findById(Long.parseLong(tokens[0])).get();
				InterestTopic i = interestTopicRepository.findById(Integer.parseInt(tokens[1])).get();
				wt.setInterestTopic(i);
				wt.setWelfare(w);
				welfareInterestTopicRepository.save(wt);
			}
		}
	}
}
