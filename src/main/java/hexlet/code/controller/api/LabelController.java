package hexlet.code.controller.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hexlet.code.dto.label.LabelCreateDTO;
import hexlet.code.dto.label.LabelDTO;
import hexlet.code.dto.label.LabelUpdateDTO;
import hexlet.code.service.LabelService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/labels")
@AllArgsConstructor
public class LabelController {

    private LabelService labelService;

    /**
     * GET /api/labels/{id}.
     *
     * @param id
     * @return Label.
     */
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelDTO show(@PathVariable Long id) {
        return labelService.show(id);
    }

    /**
     * GET /api/labels.
     *
     * @return List of labels.
     */
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LabelDTO>> showAll() {
        var result = labelService.showAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .body(result);
    }

    /**
     * POST /api/labels.
     *
     * @param label
     * @return Label.
     */
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public LabelDTO create(@RequestBody @Valid LabelCreateDTO label) {
        return labelService.create(label);
    }

    /**
     * PUT /api/labels/{id}.
     *
     * @param id
     * @param label
     * @return Label.
     */
    @RequestMapping(path = "/{id}", method = { RequestMethod.PUT, RequestMethod.PATCH })
    @ResponseStatus(HttpStatus.OK)
    public LabelDTO update(@PathVariable Long id, @RequestBody @Valid LabelUpdateDTO label) {
        return labelService.update(id, label);
    }

    /**
     * DELETE /api/labels/{id}.
     *
     * @param id
     */
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        labelService.delete(id);
    }
}
