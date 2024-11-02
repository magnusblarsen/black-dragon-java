package dk.acto.blackdragon.service;

import java.net.URL;

import dk.acto.blackdragon.model.AuthorData;

public class AuthorDataFactoryImpl implements AuthorDataFactory {
    @Override
    public AuthorData create() {
        try {
            return AuthorData.builder()
                .name("Magnus Brun Larsen")
                .linkedInProfile(new URL("www.linkedin.com/in/magnus-brun-larsen-36a5491b4"))
                .solutionRepository(new URL("https://github.com/magnusblarsen/black-dragon-java")).build();
        } catch (Exception e) {
            System.err.println("Couldn't make author-data: " + e.getMessage());
            return AuthorData.builder().build();
        }
    }
}