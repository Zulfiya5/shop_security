package uz.pdp.shop.projection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.rest.core.config.Projection;
import uz.pdp.shop.entity.developer.DeveloperEntity;

@Projection(
        name = "developer",
        types = { DeveloperEntity.class })
@JsonIgnoreProperties({"_links"})
public interface DeveloperProject {

    @JsonProperty("full_name")
    String getName();

    @JsonProperty("password")
    String getPassword();
}
