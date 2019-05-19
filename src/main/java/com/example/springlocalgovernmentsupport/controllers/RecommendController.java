package com.example.springlocalgovernmentsupport.controllers;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class RecommendController {

    @GetMapping("/recommend")
    public List<RecommendedItem> recommend() throws IOException, TasteException {
        //Creating data model
        File file = new ClassPathResource("data/dataset.csv").getFile();
        DataModel datamodel = new FileDataModel(file); //data

        //Creating UserSimilarity object.
        UserSimilarity usersimilarity = new PearsonCorrelationSimilarity(datamodel);

        //Creating UserNeighbourHHood object.
        UserNeighborhood userneighborhood = new ThresholdUserNeighborhood(3.0, usersimilarity, datamodel);

        //Create UserRecomender
        UserBasedRecommender recommender = new GenericUserBasedRecommender(datamodel, userneighborhood, usersimilarity);

        List<RecommendedItem> recommendations = recommender.recommend(2, 3);

        for (RecommendedItem recommendation : recommendations) {
            System.out.println("recommendation ==> " + recommendation);
        }

        // Item
        return recommendations;
    }
}
