package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;
import com.example.familytree.domain.Question;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.question.QuestionReqDTO;
import com.example.familytree.repository.QuestionRepository;
import com.example.familytree.service.MemberService;
import com.example.familytree.service.QuestionService;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Question service.
 */
@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {
  private final QuestionRepository questionRepository;
  private final MemberService memberService;

  /**
   * Instantiates a new Question service.
   *
   * @param questionRepository the question repository
   */
  public QuestionServiceImpl(@Lazy QuestionRepository questionRepository,
      @Lazy MemberService memberService) {
    super();
    this.questionRepository = questionRepository;
    this.memberService = memberService;
  }

  /**
   * Thêm mới một yêu cầu
   *
   * @param reqDTO
   * @return
   * @throws FamilyTreeException
   * @since 07/07/2023
   */
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

  /**
   * DS tất cả các yêu cầu
   *
   * @return
   * @throws FamilyTreeException
   * @author nga
   * @since 07/07/2023
   */
  @Override
  public List<Question> getALl() throws FamilyTreeException {
    var questions = questionRepository.findAll();
    return questions;
  }

  /**
   * DS các yêu cầu của một thành viên
   *
   * @param memberId
   * @return
   * @throws FamilyTreeException
   * @author nga
   * @since 07/07/2023
   */
  @Override
  public List<Question> getAllForAMember(Long memberId) throws FamilyTreeException {
    memberService.getMemberById(memberId);
    var questions  = questionRepository.findAllByMemberId(memberId);
    return questions;
  }

}
