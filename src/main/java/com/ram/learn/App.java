package com.ram.learn;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by failedOptimus on 23-12-2017.
 */
public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("beans.xml");
        OffersDao offersDao = (OffersDao) applicationContext.getBean("offersDao");

        /*Offers offer1 = new Offers("Pankaj", "pankaj@gmail.com", "I write Javascript");
        Offers offer2 = new Offers("Pavan", "pavanj@gmail.com", "I write python");
        Offers offer3 = new Offers("Umesh", "Umeshj@gmail.com", "I write Java");
        offersDao.createOffer(offer1);
        offersDao.createOffer(offer2);
        offersDao.createOffer(offer3);*/
        Offers offerr = new Offers(6, "Chinga", "chinga@dinga.com", "I fuck! ");
        offersDao.updateOffer(offerr);

        Offers of1 = new Offers("ch", "ch", "ch");
        Offers of2 = new Offers("ch", "ch", "ch");
        Offers of3 = new Offers("ch", "ch", "ch");
        Offers of4 = new Offers("ch", "ch", "ch");

        List<Offers> offersList = new ArrayList<Offers>();
        offersList.add(of1);
        offersList.add(of2);
        offersList.add(of3);
        offersList.add(of4);
        offersDao.createOffers(offersList);

        List<Offers> offers = offersDao.getOffers();
        for(Offers offer : offers) {
            System.out.println(offer);
        }

    }
}