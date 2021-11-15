package recipes.service;

import org.springframework.http.ResponseEntity;
import recipes.model.Recipe;
import recipes.model.User;

import java.util.List;

public interface RecipesService {

    Recipe getRecipeById(Long id);

    List<Recipe> getRecipeByName(String name);

    List<Recipe> getRecipeByCategory(String category);

    Recipe saveRecipe(Recipe recipe);

    ResponseEntity<Object> updateRecipe(Long id, Recipe recipe);

    ResponseEntity<Object> deleteRecipe(Long id);
}
