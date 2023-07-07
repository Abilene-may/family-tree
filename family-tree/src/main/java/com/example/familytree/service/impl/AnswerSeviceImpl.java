package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;
import com.example.familytree.domain.Answer;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.answer.AnswerDTO;
import com.example.familytree.models.answer.AnswerReqDTO;
import com.example.familytree.repository.AnswerRepository;
import com.example.familytree.repository.EventManagementRepository;
import com.example.familytree.repository.ExpenseDetailRepository;
import com.example.familytree.service.AnswerService;
import com.example.familytree.service.GuestManagementService;
import com.example.familytree.service.QuestionService;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *Service xử lý logic màn quản lý phản hồi
 *
 * @author nga
 * @since 07/07/2023
 */

@Service
@Slf4j
public class AnswerSeviceImpl implements AnswerService {
  private final AnswerRepository answerRepository;
  private final QuestionService questionService;

  public AnswerSeviceImpl(
      @Lazy AnswerRepository answerRepository,
      @Lazy QuestionService questionService){
    super();
    this.answerRepository = answerRepository;
    this.questionService = questionService;
  }

  /**
   * Tạo 1 phản hồi cho một yêu cầu
   *
   * @param reqDTO
   * @return
   * @throws FamilyTreeException
   * @author nga
   * @since 07/07/2023
   */
  @Override
  @Transactional
  public AnswerDTO create(AnswerReqDTO reqDTO) throws FamilyTreeException {
    var question = questionService.getById(reqDTO.getQuestionId());
    if (question.getStatus().equals(Constant.DA_PHAN_HOI)) {
      throw new FamilyTreeException(
          ExceptionUtils.Q_HAS_BEEN_ANSWERED,
          ExceptionUtils.messages.get(ExceptionUtils.Q_HAS_BEEN_ANSWERED));
    }
    LocalDate today = LocalDate.now();
    // trả ra phản hồi và câu hỏi
    AnswerDTO answerDTO =
        AnswerDTO.builder()
            .question(question)
            .contentResponse(reqDTO.getContent())
            .sendDateResponse(today)
            .respondersId(reqDTO.getRespondersId())
            .build();
    Answer answer =
        Answer.builder()
            .content(reqDTO.getContent())
            .sendDate(today)
            .questionId(reqDTO.getQuestionId())
            .memberId(question.getMemberId())
            .respondersId(reqDTO.getRespondersId())
            .build();
    question.setStatus(Constant.DA_PHAN_HOI);
    answerRepository.save(answer);
    return answerDTO;
  }

  /**
   * Xem thông tin 1 phản hồi theo id của yêu cầu
   *
   * @param questionId
   * @return
   * @throws FamilyTreeException
   * @since 07/07/2023
   */
  @Override
  public AnswerDTO getByQuestionId(Long questionId) throws FamilyTreeException {
    // check câu hỏi
    var question = questionService.getById(questionId);
    var optional = answerRepository.findByQuestionId(questionId);
    if (optional.isEmpty()) {
      throw new FamilyTreeException(
          ExceptionUtils.ANSWER_DOES_NOT_EXITS,
          ExceptionUtils.messages.get(ExceptionUtils.ANSWER_DOES_NOT_EXITS));
    }
    var answer = optional.get();
    AnswerDTO answerDTO =
        AnswerDTO.builder()
            .question(question)
            .contentResponse(answer.getContent())
            .sendDateResponse(answer.getSendDate())
            .respondersId(answer.getRespondersId())
            .build();
    return answerDTO;
  }
}
