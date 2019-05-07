package com.jacobsiau.web.repositories;

import java.util.ArrayList;
import java.util.List;
import com.jacobsiau.web.mappers.RobotDataMapper;
import com.jacobsiau.web.models.RobotData;
import com.jacobsiau.web.models.RobotDepthData;
import com.jacobsiau.web.models.RobotDepthDataWrapper;
import com.jacobsiau.web.models.RobotImageData;
import com.jacobsiau.web.models.RobotImageDataEntry;
import com.jacobsiau.web.models.RobotImageDataWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;

/**
 * RobotDataRepository is the repository layer of this application.
 * It performs any business logic needed, and delegates method calls inbetween the controller and the persistence layer. 
 */
@Repository
public class RobotDataRepository {

    private Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private RobotDataMapper _robotDataMapper;

    // Attaching the mapping interface to be used by this repository layer
    public RobotDataRepository(RobotDataMapper _robotDataMapper) {
        this._robotDataMapper = _robotDataMapper;
    }

    /**
     * getAllRobotData calls the method in {@link RobotDataMapper} to get all depth data from the database.
     */
    public List<RobotData> getAllRobotData() {
        LOGGER.debug("getAllRobotData called.");
        List<RobotData> res = new ArrayList<>();
        res = _robotDataMapper.getAllRobotData();
        return res;
    }

    /**
     * getAllRobotDepthData calls the method in {@link RobotDataMapper} to get all robot depth data from the database.
     */
    public List<RobotDepthData> getAllRobotDepthData() {
        LOGGER.debug("getAllRobotDepthData called.");
        List<RobotDepthData> res = new ArrayList<>();
        res = _robotDataMapper.getAllRobotDepthData();
        return res;
    }

    /**
     * deleteAllRobotDepthData calls the method in {@link RobotDataMapper} to delete all depth data from the database. 
     */
    public void deleteAllRobotDepthData() {
        LOGGER.debug("deleteAllRobotDepthData called.");
        LOGGER.debug("Deleting robot depth data...");
        _robotDataMapper.deleteAllRobotDepthData();
        LOGGER.debug("Updating numbering scheme...");
        _robotDataMapper.updateRobotDepthDataRestartNumbering();
        LOGGER.debug("Done deleting all data in ROBOT_DEPTH_DATA.");
    }

    /**
     * insertRobotDepthData takes a list of {@link RobotDepthData} as wrapped by {@link RobotDepthDataWrapper} and
     * calls the {@link RobotDataMapper} method to insert them, one by one, into the database.
     */
    public void insertRobotDepthData(RobotDepthDataWrapper data) {
        LOGGER.debug("insertRobotDepthData called.");
        for(RobotDepthData r : data.getDepthData()) {
            _robotDataMapper.insertRobotDepthData(r.getDepth());
        }
        LOGGER.debug("Done inserting data into ROBOT_DEPTH_DATA.");
    }

    // IMAGE DATA METHODS BELOW

    public void insertRobotImageData(RobotImageDataWrapper data) {
        LOGGER.debug("insertRobotImageData called.");
        for(RobotImageData r : data.getImageData()) {
            _robotDataMapper.insertRobotImageData(data.getXpos(), data.getYpos(), r.getDepth());
        }
    }

    public void deleteAllRobotImageData() {
        LOGGER.debug("deleteAllRobotImageData called.");
        LOGGER.debug("Deleting robot image data...");
        _robotDataMapper.deleteAllRobotImageData();
        LOGGER.debug("Updating numbering scheme...");
        _robotDataMapper.updateRobotImageDataRestartNumbering();
        LOGGER.debug("Done deleting all data in ROBOT_IMAGE_DATA.");
    }

    public List<RobotImageDataEntry> getAllRobotImageData() {
        LOGGER.debug("getAllRobotImageData called.");
        List<RobotImageDataEntry> res = new ArrayList<>();
        res = _robotDataMapper.getAllRobotImageData();
        return res;
    }
    
}
