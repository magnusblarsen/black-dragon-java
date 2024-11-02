package dk.acto.blackdragon.service;

import io.vavr.collection.List;

import dk.acto.blackdragon.model.Model;

public class ModelFactoryImpl implements ModelFactory<Model> {

    @Override
    public List<Model> parse(String string) {
        String[] input = string.split("\n");
        List<Model> result = List.empty();
        for (int i = 1; i < input.length; i ++) {
            var line = input[i].split(", ");
            var modelBuilder = Model.builder()
                .id(Long.parseLong(line[0]))
                .weight(Double.parseDouble(line[1]))
                .cost(Integer.parseInt(line[2]))
                .inventory(Long.parseLong(line[3]));

            result = result.append(modelBuilder.build());
        }
        return result;
    }
}