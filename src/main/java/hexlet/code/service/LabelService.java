package hexlet.code.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hexlet.code.dto.label.LabelCreateDTO;
import hexlet.code.dto.label.LabelDTO;
import hexlet.code.dto.label.LabelUpdateDTO;
import hexlet.code.exception.ResourceDeletionException;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LabelService {

    private LabelMapper labelMapper;
    private LabelRepository labelRepository;
    private TaskRepository taskRepository;

    /**
     * GET /api/labels/{id}.
     *
     * @param id
     * @return Label
     */
    public LabelDTO show(Long id) {
        return labelMapper.map(labelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label not found")));
    }

    /**
     * GET /api/labels.
     *
     * @return Label list
     */
    public List<LabelDTO> showAll() {
        var labels = labelRepository.findAll();
        return labels.stream()
                .map(labelMapper::map)
                .toList();
    }

    /**
     * POST /api/labels.
     *
     * @param labelDTO
     * @return Label
     */
    public LabelDTO create(LabelCreateDTO labelDTO) {
        return labelMapper.map(
                labelRepository.save(
                        labelMapper.map(labelDTO)));
    }

    /**
     * PUT /api/labels/{id}.
     *
     * @param id
     * @param dto
     * @return Label
     */
    public LabelDTO update(long id, LabelUpdateDTO dto) {
        var label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label not found"));
        labelMapper.update(dto, label);
        labelRepository.save(label);
        return labelMapper.map(label);
    }

    /**
     * DELETE /api/labels/{id}.
     *
     * @param id
     */
    public void delete(long id) {
        var label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label not found"));
        if (taskRepository.existsByLabelsContaining(label)) {
            throw new ResourceDeletionException("Нельзя удалить метку которая связана с задачей");
        }
        labelRepository.deleteById(id);
    }
}
