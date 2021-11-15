package recipes.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.Recipe;
import recipes.service.RecipesService;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
public class RecipesController {

    RecipesService recipesService;

    @Autowired
    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Recipe recipe = recipesService.getRecipeById(id);
        return recipe == null ? ResponseEntity.notFound().build() :
                new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<Recipe> getRecipeByNameOrCategory(@RequestParam(defaultValue = "") String name,
                                                  @RequestParam(defaultValue = "") String category) {

        if ((name.equals("")) == (category.equals("")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (!name.equals(""))
            return recipesService.getRecipeByName(name);
        return recipesService.getRecipeByCategory(category);
    }

    @PostMapping("new")
    public Map<String, Long> saveRecipe(@Valid @RequestBody Recipe recipe) {
        return Map.of("id", recipesService.saveRecipe(recipe).getId());
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateRecipe(@PathVariable Long id,
                                               @Valid @RequestBody Recipe recipe) {

        return recipesService.updateRecipe(id, recipe);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable Long id) {
        return recipesService.deleteRecipe(id);
    }
}
