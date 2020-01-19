package org.VehicleShopUI.service;


import org.VehicleShopUI.entity.EngineType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EngineTypeService {


    private RestTemplate restTemplate;

    private List<EngineType> engineTypes;

    public List<EngineType> getEngineTypes() {
        return engineTypes;
    }

    public EngineTypeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EngineType getEngineTypeByTitle(Optional<EngineType> firstSelectedItem) {
        String currentEngineTypeTitle = firstSelectedItem.get().getEngineTypeTitle();
        if (currentEngineTypeTitle!=null) {
            Optional<EngineType> engineTypeFromListByTitle = engineTypes.stream()
                    .filter(engineType -> engineType.getEngineTypeTitle().equals(currentEngineTypeTitle))
                    .findFirst();
            if (engineTypeFromListByTitle.isPresent()){
                return engineTypeFromListByTitle.get();
            }
        }
        return null;
    }

    public EngineType getEngineTypeById(Optional<EngineType> firstSelectedItem) {
        Long currentEngineTypeId = firstSelectedItem.get().getEngineTypeId();
        //По id ищу объект в закешированом списке по id
        if (currentEngineTypeId!=null) {
            Optional<EngineType> engineTypeFromListById = engineTypes.stream()
                    .filter(engineType -> engineType.getEngineTypeId().equals(currentEngineTypeId))
                    .findFirst();
            if (engineTypeFromListById.isPresent()) {
                return engineTypeFromListById.get();
            }
        }
        return null;
    }

    public List<EngineType> getAll() {
        refreshCashedEngineTypes();
        return engineTypes;
    }

    public void refreshCashedEngineTypes() {
        ResponseEntity<List<EngineType>> engineTypeResponse
                = restTemplate.exchange("http://localhost:8080/engineType",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<EngineType>>() {
                });
        engineTypes = engineTypeResponse.getBody();
    }

    public void remove(EngineType engineType){
        Map<String, String> params = new HashMap<>();
        params.put("id", engineType.getEngineTypeId().toString());
        restTemplate.delete("http://localhost:8080/engineType/{id}", params);
    }

    public void save(EngineType engineType){
        HttpHeaders headers = createJsonHeaders();
        HttpEntity<EngineType> requestBody = new HttpEntity<>(engineType, headers);
        restTemplate.put("http://localhost:8080/engineType/change/"+ engineType.getEngineTypeId(), requestBody, EngineType.class);
    }


    public EngineType create(EngineType engineType){
        HttpHeaders headers = createJsonHeaders();
        HttpEntity<EngineType> requestBody = new HttpEntity<>(engineType, headers);
        EngineType engineTypeFromAPI = restTemplate.postForObject("http://localhost:8080/engineType", requestBody, EngineType.class);
        return engineTypeFromAPI;
    }

    private HttpHeaders createJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
