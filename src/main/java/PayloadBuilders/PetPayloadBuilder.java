package PayloadBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.List;

public class PetPayloadBuilder {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static ObjectNode createPetPayload(int id, String name, String status,
                                              int categoryId, String categoryName,
                                              List<String> photoUrls, List<TagData> tags) {

        ObjectNode pet = mapper.createObjectNode();

        pet.put("id", id);
        pet.put("name", name);
        pet.put("status", status);

        ObjectNode category = pet.putObject("category");
        category.put("id", categoryId);
        category.put("name", categoryName);

        ArrayNode photos = pet.putArray("photoUrls");
        photoUrls.forEach(photos::add);

        ArrayNode tagsArray = pet.putArray("tags");
        for (TagData tag : tags) {
            ObjectNode tagObj = tagsArray.addObject();
            tagObj.put("id", tag.id());
            tagObj.put("name", tag.name());
        }

        return pet;
    }

    // Small helper class to hold tag data
    public record TagData(int id, String name) {}
}


/*
in steps --- ObjectNode petPayload = PayloadBuilder.createPetPayload(
    101, "Tommy", "available",
    1, "Dogs",
    List.of("http://example.com/photo1.jpg", "http://example.com/photo2.jpg"),
    List.of(new TagData(2001, "cute"), new TagData(2002, "friendly"))
);
 */