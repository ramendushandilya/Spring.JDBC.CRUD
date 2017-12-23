package com.ram.learn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by failedOptimus on 23-12-2017.
 */

@Component("offersDao")
public class OffersDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Get all the offers
     * @return
     */
    public List<Offers> getOffers() {

        return jdbcTemplate.query("select * from offers", new RowMapper<Offers>() {
            public Offers mapRow(ResultSet resultSet, int i) throws SQLException {
                Offers offers = new Offers();
                offers.setId(resultSet.getInt("id"));
                offers.setName(resultSet.getString("name"));
                offers.setEmail(resultSet.getString("email"));
                offers.setText(resultSet.getString("text"));
                return offers;
            }
        });
    }

    /**
     * Gets a single offer by id
     * @param id
     * @return
     */
    public Offers getOfferById(int id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        return jdbcTemplate.queryForObject("select * from offers where id = :id", mapSqlParameterSource, new RowMapper<Offers>() {
            public Offers mapRow(ResultSet resultSet, int i) throws SQLException {
                Offers offers = new Offers();
                offers.setId(resultSet.getInt("id"));
                offers.setName(resultSet.getString("name"));
                offers.setEmail(resultSet.getString("email"));
                offers.setText(resultSet.getString("text"));
                return offers;
            }
        });
    }

    /**
     * Deletes an offer by id
     * @param id
     */
    public void deleteById(int id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("id", id);
        if(jdbcTemplate.update("delete from offers where id = :id", mapSqlParameterSource) == 1) {
            System.out.println("One record deleted successfully");
        } else {
            System.out.println("No record deleted");
        }
    }

    /**
     * Creates an offer
     * @param offer
     */
    public void createOffer(Offers offer) {
        BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(offer);
        if(jdbcTemplate.update("insert into offers (name, email, text) values (:name, :email, :text)", beanPropertySqlParameterSource) == 1) {
            System.out.println("One record added");
        } else {
            System.out.println("No record added");
        }
    }

    /**
     * Updates an offer
     * @param offer
     */
    public void updateOffer(Offers offer) {
        BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(offer);
        if(jdbcTemplate.update("update offers set name = :name, email = :email, text = :text where id = :id", beanPropertySqlParameterSource) == 1) {
            System.out.println("One record updated");
        } else {
            System.out.println("No record updated");
        }
    }

    /**
     * Bach creation of offers
     * Transactional annotation makes sure that the action happens in a batch or don't happen
     * @param offers
     * @return
     */
    @Transactional
    public int[] createOffers(List<Offers> offers) {
        SqlParameterSource[] sqlParameterSources = SqlParameterSourceUtils.createBatch(offers.toArray());
        return jdbcTemplate.batchUpdate("insert into offers (name, email, text) values (:name, :email, :text)", sqlParameterSources);
    }
}