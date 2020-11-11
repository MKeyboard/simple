package com.cmschina.elasticsearch.entity.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@Document(indexName = "bank",type = "account",shards = 1,replicas = 0)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EsAccount implements Serializable {

    @Id
    private Long account_number;
    @Field(type = FieldType.Long)
    private Long balance;
    @Field(type = FieldType.Text)
    private String firstname;
    @Field(type = FieldType.Text)
    private String lastname;
    @Field(type = FieldType.Long)
    private Long age;
    @Field(type = FieldType.Text)
    private String gender;
    @Field(type = FieldType.Text)
    private String address;
    @Field(type = FieldType.Text)
    private String employer;
    @Field(type = FieldType.Text)
    private String email;
    @Field(type = FieldType.Text)
    private String city;
    @Field(type = FieldType.Text)
    private String state;

}
