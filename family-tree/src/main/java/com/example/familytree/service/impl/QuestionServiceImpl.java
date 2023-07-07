package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;
import com.example.familytree.domain.Question;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.common.ErrorDTO;
import com.example.familytree.models.question.QuestionReqDTO;
import com.example.familytree.repository.QuestionRepository;
import com.example.familytree.service.QuestionService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * The type Question service.
 */
@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {
  private final QuestionRepository questionRepository;

  /**
   * Instantiates a new Question service.
   *
   * @param questionRepository the question repository
   */
  public QuestionServiceImpl(@Lazy QuestionRepository questionRepository) {
    super();
    this.questionRepository = questionRepository;
  }

  @Override
  @Transactional
  public Question create(QuestionReqDTO reqDTO) throws FamilyTreeException {
    LocalDate today = LocalDate.now();
    Question question =
        Question.builder()
            .title(reqDTO.getTitle())
            .reason(reqDTO.getReason())
            .content(reqDTO.getContent())
            .memberId(reqDTO.getMemberId())
            .sendDate(today)
            .status(Constant.CHO_PHE_DUYET).build();
    questionRepository.save(question);
    return question;
  }

  /**
   * Sửa thông tin 1 yêu cầu
   *
   * @param question
   * @return
   * @throws FamilyTreeException
   */
  @Override
  public Question update(Question question) throws FamilyTreeException {
    var questionCheck = this.getById(question.getId());
    if (!questionCheck.getStatus().equals(Constant.CHO_PHE_DUYET)) {
      throw new FamilyTreeException(
          ExceptionUtils.Q_OTHER_STATUS_WAITING_FOR_APPROVAL,
          ExceptionUtils.messages.get(ExceptionUtils.Q_OTHER_STATUS_WAITING_FOR_APPROVAL));
    }
    questionRepository.save(question);
    return question;
  }

  /**
   * Xem thông tin một yêu cầu theo id
   *
   * @param id
   * @throws FamilyTreeException
   * @since 07/07/2023
   */
  @Override
  public Question getById(Long id) throws FamilyTreeException {
    var optional = questionRepository.findById(id);
    if (optional.isEmpty()) {
      throw new FamilyTreeException(
          ExceptionUtils.QUESTION_IS_NOT_EXITS,
          ExceptionUtils.messages.get(ExceptionUtils.QUESTION_IS_NOT_EXITS));
    }
    return optional.get();
  }

  /**
   * Xóa một yêu cầu theo id
   *
   * @param id
   * @throws FamilyTreeException
   * @author nga
   * @since 07/07/2023
   */
  @Override
  public void delete(Long id) throws FamilyTreeException {
    var question = this.getById(id);
    if (!question.getStatus().equals(Constant.CHO_PHE_DUYET)) {
      throw new FamilyTreeException(
          ExceptionUtils.Q_OTHER_STATUS_WAITING_FOR_APPROVAL,
          ExceptionUtils.messages.get(ExceptionUtils.Q_OTHER_STATUS_WAITING_FOR_APPROVAL));
    }
    questionRepository.delete(question);
  }

}
