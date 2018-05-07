package com.intellinote.api;

import com.google.cloud.language.v1.AnalyzeEntitiesResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.EncodingType;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.intellinote.keyword.Keyword;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class LangTest {

    @Test
    public void test() throws IOException {
        LanguageServiceClient languageServiceClient = LanguageServiceClient.create();
        Document doc = Document.newBuilder()
                .setContent("Finland's population is 5.5 million (2016), and the majority of the population is concentrated in the southern region.[8] 88.7% of the population is Finnish and speaks Finnish, a Uralic language unrelated to the Scandinavian languages; next come the Finland-Swedes (5.3%). Finland is the eighth-largest country in Europe and the most sparsely populated country in the European Union. It is a parliamentary republic with a central government based in the capital city of Helsinki, local governments in 311 municipalities,[9] and one autonomous region, the Åland Islands. Over 1.4 million people live in the Greater Helsinki metropolitan area, which produces one third of the country's GDP.").setType(Document.Type.PLAIN_TEXT).build();
        // Detects the sentiment of the text
        AnalyzeEntitiesResponse sr = languageServiceClient.analyzeEntities(doc, EncodingType.UTF8);
        System.out.println(sr.getEntitiesList());
    }
}
