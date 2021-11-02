package com.app.epolice.service;

import com.app.epolice.model.entity.policestation.PoliceStation;
import com.app.epolice.repository.PoliceStationRepository;
import com.app.epolice.util.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoliceStationService {
    /**
     * Initializing the objects
     */
    PoliceStationRepository policeStationRepository;

    /**
     * Parameterized constructors
     * @param policeStationRepository
     */
    public PoliceStationService(PoliceStationRepository policeStationRepository) {
        this.policeStationRepository = policeStationRepository;
    }

    /**
     * Fetching all the police stations from the database
     *
     * @return
     */
    public ResponseEntity<Object> listAllPoliceStations() {
        try {
            List<PoliceStation> policeStationList = policeStationRepository.findPoliceStationsByActive(true);
            if (policeStationList.isEmpty()) {
                return new ResponseEntity<>("There are no police stations in the database", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(policeStationList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method is storing the list of police stations in the database
     * @param policeStationList
     * @return
     */
    public ResponseEntity<Object> addNewPoliceStations(List<PoliceStation> policeStationList) {
        try {
            if (policeStationList.isEmpty()) {
                return new ResponseEntity<>("You are entering empty list", HttpStatus.BAD_REQUEST);
            } else {
                for (PoliceStation policeStation:policeStationList
                     ) {
                    policeStation.setCreatedDate(DateTime.getDateTime());
                    policeStation.setActive(true);
                    policeStationRepository.save(policeStation);
                }
                if(policeStationList.size()==1){
                    return new ResponseEntity<>("Police station is successfully added", HttpStatus.OK);
                }else{
                    return new ResponseEntity<>("Police stations are successfully added", HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This service is deleting the PoliceStations from the database
     * @param policeStationList
     * @return
     */
    public ResponseEntity<Object> deletePoliceStation(List<PoliceStation> policeStationList){
        try{
            if(policeStationList.isEmpty()){
                return new ResponseEntity<>("No police station is selected for the deletion",HttpStatus.BAD_REQUEST);
            }else{
                for (PoliceStation policeStation:policeStationList
                     ) {
                    policeStation.setActive(false);
                    policeStation.setUpdatedDate(DateTime.getDateTime());
                    policeStationRepository.save(policeStation);
                }
                if(policeStationList.size()==1){
                    return new ResponseEntity<>("Police station is successfully deleted",HttpStatus.OK);
                }else{
                    return new ResponseEntity<>("Police stations are successfully deleted",HttpStatus.OK);
                }
            }
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This service is updating the police station in the database.
     * @param policeStation
     * @return
     */
    public ResponseEntity<Object> updatePoliceStation(PoliceStation policeStation){
        try{
            if(null==policeStation){
                return new ResponseEntity<>("Null object passed in the body",HttpStatus.BAD_REQUEST);
            }else{
                policeStation.setUpdatedDate(DateTime.getDateTime());
                policeStationRepository.save(policeStation);
                return new ResponseEntity<>("Police station is successfully updated.", HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}