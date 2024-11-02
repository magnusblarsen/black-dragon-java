package dk.acto.blackdragon.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.stream.Stream;

import dk.acto.blackdragon.model.Model;
import dk.acto.blackdragon.model.Stats;
import io.vavr.collection.List;

public class ModelTransformerImpl implements ModelTransformer<Model, Stats> {

    @Override
    public Stats transform(List<Model> model) {
        var statsBuilder = Stats.builder();
        Stream<Long> ids = model.toJavaStream().map(m -> m.getId());
        Stream<BigDecimal> costs = model.toJavaStream().map(m -> BigDecimal.valueOf(((double)m.getCost())/100.0));
        Stream<BigInteger> inventories = model.toJavaStream().map(m -> BigInteger.valueOf(m.getInventory()));
        
        int evenIds = ids.filter(id -> id % 2 == 0).toList().size();
        int oddIds = ids.filter(id -> id % 2 == 1).toList().size();

        statsBuilder.evenIds(BigInteger.valueOf(evenIds));
        statsBuilder.oddIds(BigInteger.valueOf(oddIds));

        BigDecimal bigSum = costs.reduce(BigDecimal.ZERO, BigDecimal::add);
        statsBuilder.meanCost(bigSum.divide(BigDecimal.valueOf(costs.toList().size())));


        BigDecimal weightInventory = model
            .map(m -> BigDecimal.valueOf(m.getInventory()).multiply(BigDecimal.valueOf(m.getWeight())))
            .fold(BigDecimal.ZERO, BigDecimal::add);

        statsBuilder.weightedInventory(weightInventory);

        statsBuilder.totalInventory(inventories.reduce(BigInteger.ZERO, BigInteger::add));

        return statsBuilder.build();
    }
}
