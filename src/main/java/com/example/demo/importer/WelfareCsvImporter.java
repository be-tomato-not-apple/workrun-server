// package com.example.demo.importer;
//
// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.nio.charset.StandardCharsets;
//
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;
//
// import java.util.ArrayList;
// import java.util.List;
// import java.util.function.Function;
// import java.util.stream.Collectors;
//
// import com.example.demo.domain.Welfare;
// import com.example.demo.domain.mapping.WelfareHomeStatus;
// import com.example.demo.domain.mapping.WelfareInterestTopic;
// import com.example.demo.repository.HomeStatusRepository;
// import com.example.demo.repository.InterestTopicRepository;
// import com.example.demo.repository.WelfareHomeStatusRepository;
// import com.example.demo.repository.WelfareInterestTopicRepository;
// import com.example.demo.repository.WelfareRepository;
// import com.example.demo.domain.HomeStatus;
// import com.example.demo.domain.InterestTopic;
//
// import lombok.RequiredArgsConstructor;
//
// @Component
// @RequiredArgsConstructor
// @Order(1)
// public class WelfareCsvImporter implements CommandLineRunner {
//
// 	private final WelfareRepository welfareRepository;
// 	private final HomeStatusRepository homeStatusRepository;
// 	private final WelfareHomeStatusRepository welfareHomeStatusRepository;
// 	private final WelfareInterestTopicRepository welfareInterestTopicRepository;
// 	private final InterestTopicRepository interestTopicRepository;
//
//
// 	@Override
// 	public void run(String... args) throws Exception {
//
// 		insertDefaultHomeStatus();
// 		insertDefaultInterestTopics();
//
// 		loadWelfareFromCsv();
//
// 		loadWelfareHomeStatusMapping();
// 		loadWelfareInterestTopicMapping();
// 	}
//
// 	private void insertDefaultHomeStatus() {
// 		if (homeStatusRepository.count() > 0) return;
// 		List<String> statuses = List.of("장애인", "저소득", "한부모/조손", "다문화/탈북민", "기타");
// 		statuses.forEach(content -> homeStatusRepository.save(new HomeStatus(null, content)));
// 	}
//
// 	private void insertDefaultInterestTopics() {
// 		if (interestTopicRepository.count() > 0) return;
// 		List<String> topics = List.of("신체건강", "생활지원", "서민금융", "임신/출산", "정신건강", "교육", "문화/여가", "일자리", "주거");
// 		topics.forEach(content -> interestTopicRepository.save(new InterestTopic(null, content)));
// 	}
//
// 	private void loadWelfareFromCsv() throws Exception {
//
// 		if (welfareRepository.count() > 0) ;
//
// 		try (BufferedReader br = new BufferedReader(new InputStreamReader(
// 			getClass().getResourceAsStream("/central_welfare.csv"), StandardCharsets.UTF_8))) {
//
// 			br.readLine(); // skip header
// 			String line;
// 			while ((line = br.readLine()) != null) {
// 				String[] tokens = line.split(",", -1);
// 				Welfare w = new Welfare();
// 				w.setCenter(tokens[1]);
// 				w.setServiceName(tokens[2]);
// 				w.setContent(tokens[3]);
// 				w.setUrl(tokens[4]);
// 				w.setTarget(tokens[5].isEmpty() ? null : tokens[4]);
// 				w.setApplyMethod(tokens[6].isEmpty() ? null : tokens[5]);
// 				w.setNeedDocument(null);
//
// 				welfareRepository.save(w);
//
// 			}
// 		}
//
// 	}
//
// 	private void loadWelfareHomeStatusMapping() throws Exception {
// 		try (BufferedReader br = new BufferedReader(new InputStreamReader(
// 			getClass().getResourceAsStream("/central_welfare_home.csv"), StandardCharsets.UTF_8))) {
//
// 			br.readLine(); // skip header
// 			String line;
// 			while ((line = br.readLine()) != null) {
// 				String[] tokens = line.split(",", -1);
//
// 				WelfareHomeStatus wh = new WelfareHomeStatus();
// 				Welfare w = welfareRepository.findById(Long.parseLong(tokens[0])).get();
// 				wh.setWelfare(w);
// 				HomeStatus h = homeStatusRepository.findById(Integer.parseInt(tokens[1])).get();
// 				wh.setHomeStatus(h);
//
// 				welfareHomeStatusRepository.save(wh);
//
// 			}
// 		}
// 	}
//
// 	private void loadWelfareInterestTopicMapping() throws Exception {
// 		try (BufferedReader br = new BufferedReader(new InputStreamReader(
// 			getClass().getResourceAsStream("/central_welfare_topic.csv"), StandardCharsets.UTF_8))) {
//
// 			br.readLine(); // skip header
// 			String line;
// 			while ((line = br.readLine()) != null) {
// 				String[] tokens = line.split(",", -1);
//
// 				WelfareInterestTopic wt = new WelfareInterestTopic();
//
// 				Welfare w = welfareRepository.findById(Long.parseLong(tokens[0])).get();
// 				InterestTopic i = interestTopicRepository.findById(Integer.parseInt(tokens[1])).get();
//
// 				wt.setInterestTopic(i);
// 				wt.setWelfare(w);
//
// 				welfareInterestTopicRepository.save(wt);
// 			}
// 		}
// 	}
// }
package com.example.demo.importer;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.demo.domain.Welfare;
import com.example.demo.domain.mapping.WelfareHomeStatus;
import com.example.demo.domain.mapping.WelfareInterestTopic;
import com.example.demo.repository.HomeStatusRepository;
import com.example.demo.repository.InterestTopicRepository;
import com.example.demo.repository.WelfareHomeStatusRepository;
import com.example.demo.repository.WelfareInterestTopicRepository;
import com.example.demo.repository.WelfareRepository;
import com.example.demo.domain.HomeStatus;
import com.example.demo.domain.InterestTopic;

import lombok.RequiredArgsConstructor;
import com.opencsv.CSVReader;

@Component
@RequiredArgsConstructor
@Order(1)
public class WelfareCsvImporter implements CommandLineRunner {

	private final WelfareRepository welfareRepository;
	private final HomeStatusRepository homeStatusRepository;
	private final WelfareHomeStatusRepository welfareHomeStatusRepository;
	private final WelfareInterestTopicRepository welfareInterestTopicRepository;
	private final InterestTopicRepository interestTopicRepository;

	@Override
	public void run(String... args) throws Exception {
		if(welfareRepository.count() != 0) {
			return;
		} else{
			insertDefaultHomeStatus();
			insertDefaultInterestTopics();
			loadWelfareFromCsv();
			loadWelfareHomeStatusMapping();
			loadWelfareInterestTopicMapping();
		}

	}

	private void insertDefaultHomeStatus() {
		if (homeStatusRepository.count() > 0) return;
		var statuses = java.util.List.of("장애인", "저소득", "한부모/조손", "다문화/탈북민", "기타");
		statuses.forEach(content -> homeStatusRepository.save(new HomeStatus(null, content)));
	}

	private void insertDefaultInterestTopics() {
		if (interestTopicRepository.count() > 0) return;
		var topics = java.util.List.of("신체건강", "생활지원", "서민금융", "임신/출산", "정신건강", "교육", "문화/여가", "일자리", "주거");
		topics.forEach(content -> interestTopicRepository.save(new InterestTopic(null, content)));
	}

	private void loadWelfareFromCsv() throws Exception {
		if (welfareRepository.count() > 0) return;

		try (CSVReader reader = new CSVReader(
			new InputStreamReader(getClass().getResourceAsStream("/central_welfare.csv"), StandardCharsets.UTF_8))) {

			String[] tokens;
			reader.readNext(); // skip header

			while ((tokens = reader.readNext()) != null) {
				Welfare w = new Welfare();
				w.setCenter(tokens[1]);
				w.setServiceName(tokens[2]);
				w.setContent(tokens[3]);
				w.setUrl(tokens[4]);
				w.setTarget(tokens[5].isEmpty() ? null : tokens[5]);        // 수정됨: tokens[5]가 target
				w.setApplyMethod(tokens[6].isEmpty() ? null : tokens[6]);  // 수정됨: tokens[6]가 applyMethod
				w.setNeedDocument(tokens[7].isEmpty() ? null : tokens[7]); // needDocument도 매핑

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
