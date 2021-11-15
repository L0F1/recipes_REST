package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import recipes.model.Recipe;
import recipes.model.User;
import recipes.repository.RecipeRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipesServiceImpl implements RecipesService {

    private final RecipeRepository recipeRepository;
    private final UserService userService;

    @Autowired
    public RecipesServiceImpl(RecipeRepository recipeRepository,
                              UserService userService) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findRecipeById(id);
    }

    @Override
    public List<Recipe> getRecipeByName(String name) {
        return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    @Override
    public List<Recipe> getRecipeByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    @Override
    public Recipe saveRecipe(Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        recipe.setUser(userService.getCurrentUser());
        return recipeRepository.save(recipe);
    }

    @Override
    public ResponseEntity<Object> updateRecipe(Long id, Recipe recipe) {
        Recipe oldRecipe = recipeRepository.findRecipeById(id);

        if (oldRecipe != null) {

            User currentUser = userService.getCurrentUser();

            if (oldRecipe.getUser().getUsername().equals(currentUser.getUsername())) {
                recipe.setId(id);
                recipe.setDate(LocalDateTime.now());
                recipe.setUser(currentUser);
                recipeRepository.save(recipe);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Object> deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findRecipeById(id);

        if (recipe != null) {

            User currentUser = userService.getCurrentUser();

            if (recipe.getUser().getUsername().equals(currentUser.getUsername())) {
                recipeRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }
}
