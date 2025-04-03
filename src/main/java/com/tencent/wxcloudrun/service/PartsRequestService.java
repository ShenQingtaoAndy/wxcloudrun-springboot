package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.cons.RequestStatus;
import com.tencent.wxcloudrun.dao.PartsObjectRepository;
import com.tencent.wxcloudrun.dao.RequestRecordRepository;
import com.tencent.wxcloudrun.dto.NewQueryPartsObjectRequest;
import com.tencent.wxcloudrun.dto.SearchPartsObjectRequest;
import com.tencent.wxcloudrun.dto.SearchQueryListRequest;
import com.tencent.wxcloudrun.dto.SearchRequestRecordResponse;
import com.tencent.wxcloudrun.model.PartsObject;
import com.tencent.wxcloudrun.model.RequestRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class PartsRequestService {


    @Autowired
    private PartsObjectRepository partsObjectRepository;

    @Autowired
    private RequestRecordRepository requestRecordRepository;


    public Page<PartsObject> searchPartsObject(SearchPartsObjectRequest request){
        PageRequest page = PageRequest.of(request.getPageNumber(), request.getPageSize());
        page.withSort(Sort.by(Sort.Direction.DESC, "createTime"));
        Page<PartsObject> res = partsObjectRepository.searchPartsObject(request.getDeviceType(),
                request.getDeviceName(), request.getDeviceCategory(), request.getDeviceBrand(),
                request.getDevicePattern(),request.getSearchStr(),  page);

        return res;
    }


    @Transactional
    public void newQueryPartsObject(NewQueryPartsObjectRequest request) {

        PartsObject partsObject = new PartsObject();
        partsObject.setCreateTime(new Date());
        partsObject.setUpdateTime(partsObject.getCreateTime());
        partsObject.setDeviceName(request.getDeviceName());
        partsObject.setDeviceCategory(request.getDeviceCategory());
        partsObject.setDeviceBrand(request.getDeviceBrand());
        partsObject.setDevicePattern(request.getDevicePattern());
        partsObject.setProbPattern(request.getProbPattern());
        partsObject.setTubePattern(request.getTubePattern());
        partsObject.setPartsPattern(request.getPartsPattern());
        partsObject.setType(request.getType());
        partsObject.setIndex_str(String.join(" ", partsObject.getDeviceName(),
                partsObject.getDeviceBrand(),partsObject.getDeviceCategory(),partsObject.getDevicePattern(),
                partsObject.getPartsPattern(), partsObject.getTubePattern(),partsObject.getProbPattern()));

        Optional<PartsObject> stockParts = Optional.ofNullable(request.getPartsId())
                .flatMap(i-> partsObjectRepository.findById(i));

        RequestRecord requestRecord = new RequestRecord();

        if(stockParts.isPresent() && Objects.equals(partsObject.getType(),stockParts.get().getType() )
                && Objects.equals(stockParts.get().getIndex_str(), partsObject.getIndex_str())){
            requestRecord.setPartsId(stockParts.get().getId());
        }else{
            partsObject = partsObjectRepository.save(partsObject);
            requestRecord.setPartsId(partsObject.getId());
        }

        requestRecord.setRequestorId(request.getRequestorId());
        requestRecord.setHospitalName(request.getHospitalName());
        requestRecord.setHasCompetition(request.getHasCompetition());
        requestRecord.setHasIssue(request.getHasIssue());
        requestRecord.setRequestType(request.getRequestType());
        requestRecord.setCreateTime(partsObject.getCreateTime());
        requestRecord.setUpdateTime(requestRecord.getCreateTime());
        requestRecord.setStatus(RequestStatus.New.name());
        requestRecordRepository.save(requestRecord);
    }

    public Page<RequestRecord> searchQueryList(SearchQueryListRequest request) {

        PageRequest page = PageRequest.of(request.getPageNumber(), request.getPageSize());
        page.withSort(Sort.by(Sort.Direction.DESC, "createTime"));
        Page<RequestRecord> res = requestRecordRepository.SearchQueryPage(request.getPartsId(),
                request.getRequestorId(),  page);
        return res;

    }


    public List<RequestRecord> searchQueryListByParts(String partsId) {
        return requestRecordRepository.SearchQueryPageByParts(partsId);
    }

    public void updateRecord(RequestRecord editingRecord) {
        editingRecord.setUpdateTime(new Date());
        requestRecordRepository.save(editingRecord);
    }

    public SearchRequestRecordResponse getOneRequestRecord(String requestRecordId) {
        SearchRequestRecordResponse response = new SearchRequestRecordResponse();
        requestRecordRepository.findById(requestRecordId).ifPresent(response::setRequestRecord);

        if(null != response.getRequestRecord()){
            response.setRecords(searchQueryListByParts(response.getRequestRecord().getPartsId()));
        }

        return response;
    }
}


