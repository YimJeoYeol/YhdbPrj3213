package com.zerobase.application.service;

import com.zerobase.application.dto.VoicedataDto;
import com.zerobase.domain.Voicedata;
import com.zerobase.infrastructure.repository.VoicedataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

//import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoicedataService {

    private final VoicedataRepository voicedataRepository;

//    //기간별 검색
//    @Transactional
//    public Page<Voicedata> searchByDate(Pageable pageable, Voicedata.AdminSearchForm form){
//        log.info("s1"+form.getStart()+ form.getEnd());
//        return voicedataRepository.findByDate(pageable,form.getStart().toString(),form.getEnd().toString());
//    }

    /*admindata 넣어주는*/
    @Transactional
    public void update(Long id, VoicedataDto dto){
        Voicedata voicedata = voicedataRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("데이터가 존재하지 않습니다. id=" + id));

        voicedata.update(dto.getAdmindata());
    }

    /*페이징 처리한 전체목록*/
    @Transactional
    public Page<Voicedata> list(Pageable pageable){
        return voicedataRepository.findAllWithUser(pageable);
    }

    /*페이징 처리x 전체 목록*/
    @Transactional
    public List<VoicedataDto> findAll(){
        return voicedataRepository.findAll().stream().map(new VoicedataDto()::toDto).collect(Collectors.toList());

    }

    /*id 상세*/
    @Transactional
    public VoicedataDto details(Long id){
        Voicedata voicedata = voicedataRepository.findByIdWithUser(id);
        return voicedata.toDto();
    }

    /*Dto상세*/
    @Transactional
    public VoicedataDto findById(Long id){
        Voicedata voicedata = voicedataRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다 id= " + id));
        return voicedata.toDto();
    }

    /*재학습된 리스트만 보여주는 전체목록*/
    @Transactional
    public Page<Voicedata> findByReroll(Pageable pageable){
        return voicedataRepository.findAllContainReroll(pageable);
    }

    /*재학습 요청 service*/
    @Transactional
    public void reroll(VoicedataDto dto, Long idx, String declaration){

        URI uri = UriComponentsBuilder
                    .fromUriString("http://127.0.0.1:5000")
                    .path("/api/text/{idx}/{declaration}")
                    .encode()
                    .build()
                    .expand(idx,dto.getDeclaration())
                    .toUri();

        MultiValueMap<String, String> MVMap = new LinkedMultiValueMap<>();
        MVMap.add("text", dto.getContent());

//신호보내기
        WebClient.create().post()
                .uri(uri)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(MVMap))
                .retrieve()
                .toEntity(String.class)
                .subscribe();
    }

    /*모델 업데이트 요청 service*/
    @Transactional
    public void updatemodel(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://127.0.0.1:5000")
                .path("/api/modelupdate/")
                .encode()
                .build()
                .expand()
                .toUri();

        HttpEntity<Void> request = new HttpEntity<>(null, null);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.GET, request, Void.class);
    }

}