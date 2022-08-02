package qa.guru.demowebshop;


import com.github.javafaker.Faker;

public class TestData extends DemoWebShopsTests{

    public final static Faker faker = new Faker();

    public final static String FIRST_NAME = faker.name().firstName();
}
