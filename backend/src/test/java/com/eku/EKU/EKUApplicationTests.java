package com.eku.EKU;

import com.eku.EKU.config.CustomProperty;
import com.eku.EKU.domain.Grade;
import com.eku.EKU.domain.Lecture;
import com.eku.EKU.domain.LectureRepository;
import com.eku.EKU.secure.KeyGen;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest
class EKUApplicationTests {

    @Autowired
    private CustomProperty customProperty;

    @Autowired
    private LectureRepository lectureRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void configKeyTest(){
        String hardKey = "TE1VTEllVGdTeXBacGNVekFVd2x1aERNVElaRk9teVE=";
        String ocrKey = customProperty.getOcrKey();

        assertThat(hardKey).isEqualTo(ocrKey);
    }

    @Test
    void configURLTest(){
        String hardURL = "https://g82r5armbu.apigw.ntruss.com/custom/v1/14864/0aefe0dd10994650efabc42e9bf0bac17703fa8a15b701a5036d690aead9140b/general";
        String clovaOcrUri = customProperty.getClovaOcrUri();

        assertThat(hardURL).isEqualTo(clovaOcrUri);
    }

    @Test
    void keyGenTest(){
        try {
            SecretKey secretKey = KeyGen.generateKey(192);
            String encode = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            System.out.println(encode);
        } catch (NoSuchAlgorithmException e) {
            Assertions.fail();
        }
    }

    @Test
    void enumToCodeTest(){
        for(Grade g: Grade.values()){
            System.out.println(g.getScore());
            System.out.println(g.name());
        }
    }

    @Test
    void jsonToEntity(){
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader fileReader = new FileReader("C:\\Users\\Hyeonho\\PycharmProjects\\pb\\lecture_GE.json" , StandardCharsets.UTF_8);
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);

            ArrayList<JSONObject> lectures = (ArrayList) jsonObject.get("lectures");

            Lecture.LectureBuilder builder = Lecture.builder();
            int index=0;
            for (JSONObject lecture : lectures) {
                builder.lectureNo((String) lecture.get("lectureNo"));
                builder.lectureRoom((String) lecture.get("lectureRoom"));
                builder.lectureName((String) lecture.get("lectureName"));
                builder.lectureDesc((String) lecture.get("desc"));
                builder.lectureGroup((String) lecture.get("jojik"));
                builder.lectureTime((String) lecture.get("lectureTime"));
                builder.professor((String) lecture.get("professor"));
                builder.point(Short.parseShort((String) lecture.get("point")));
                builder.complete((String) lecture.get("complete"));
                String grade = (String) lecture.get("grade");
                if (Objects.equals(grade, "")) {
                    builder.grade(null);
                } else{
                    builder.grade(Short.parseShort(grade));
                }
                builder.campus((String) lecture.get("campus"));
                builder.year(2021);

                Lecture result = builder.build();
                lectureRepository.save(result);
            }
        } catch (ClassCastException| NullPointerException | IOException | ParseException | IllegalArgumentException e) {
            e.printStackTrace();
            fail("응 실패야");
        }


    }
}
