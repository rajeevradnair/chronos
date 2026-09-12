package com.chronos.crm.opportunity;

import com.chronos.crm.account.Account;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "opportunities")
public class Opportunity {

    @Id
    private String id;

    private String name;

    @Column(name = "opportunity_value")
    private BigDecimal value;

    private String stage;

    private Integer forecastProbability;

    private String owner;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    protected Opportunity() {
    }

    public Opportunity(
            String id,
            String name,
            BigDecimal value,
            String stage,
            Integer forecastProbability,
            String owner,
            Account account) {

        this.id = id;
        this.name = name;
        this.value = value;
        this.stage = stage;
        this.forecastProbability = forecastProbability;
        this.owner = owner;
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getStage() {
        return stage;
    }

    public Integer getForecastProbability() {
        return forecastProbability;
    }

    public String getOwner() {
        return owner;
    }

    public Account getAccount() {
        return account;
    }
}