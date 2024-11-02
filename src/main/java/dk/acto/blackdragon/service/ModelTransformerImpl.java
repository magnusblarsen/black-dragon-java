package dk.acto.blackdragon.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import dk.acto.blackdragon.model.Model;
import dk.acto.blackdragon.model.Stats;
import io.vavr.collection.List;

public class ModelTransformerImpl implements ModelTransformer<Model, Stats> {

    @Override
    public Stats transform(List<Model> model) {
        var statsBuilder = Stats.builder();
        List<Long> ids = model.map(m -> m.getId());
        List<BigDecimal> costsInCents = model.map(m -> BigDecimal.valueOf(m.getCost()));
        List<BigInteger> inventories = model.map(m -> BigInteger.valueOf(m.getInventory()));
        
        int evenIds = ids.filter(id -> id % 2 == 0).length();
        int oddIds = ids.filter(id -> id % 2 == 1).length();

        statsBuilder
            .evenIds(BigInteger.valueOf(evenIds))
            .oddIds(BigInteger.valueOf(oddIds));

        BigDecimal meanCost = costsInCents
            .fold(BigDecimal.ZERO, BigDecimal::add)
            .divide(BigDecimal.valueOf(costsInCents.toList().size()));

        statsBuilder.meanCost(meanCost.divide(BigDecimal.valueOf(100)));

        BigDecimal weightInventory = model
            .map(m -> BigDecimal.valueOf(m.getInventory()).multiply(BigDecimal.valueOf(m.getWeight())))
            .fold(BigDecimal.ZERO, BigDecimal::add);

        statsBuilder.weightedInventory(weightInventory); 

        statsBuilder.totalInventory(inventories.fold(BigInteger.ZERO, BigInteger::add));

        return statsBuilder.build();
    }
}