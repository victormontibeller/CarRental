package com.fiap.techchallenge.carrental.aluguel.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fiap.techchallenge.carrental.aluguel.DTO.AtualizarCheckListDTO;
import com.fiap.techchallenge.carrental.aluguel.entity.CheckList;
import com.fiap.techchallenge.carrental.aluguel.service.CheckListService;

@RestController
@RequestMapping(value = "/checkLists")
public class CheckListController {

   @Autowired
   CheckListService checkListService;

   private static final Logger LOGGER = LoggerFactory.getLogger(CheckListController.class);

   @PostMapping
   public ResponseEntity<CheckList> inserirCheckList(@RequestBody CheckList checkList, UriComponentsBuilder builder) {
      checkListService.inserirCheckList(checkList);

      URI location = builder.path("checkLists/{id}").buildAndExpand(checkList.getId()).toUri();
      LOGGER.info("CheckList de id {} preenchido com sucesso!", checkList.getId());

      return ResponseEntity.created(location).body(checkList);
   }

   @GetMapping("/{id}")
   public ResponseEntity<CheckList> buscarCheckList(@PathVariable long id) {
      return ResponseEntity.ok().body(checkListService.encontrarCheckList(id));
   }

   @PutMapping("/{id}")
   public ResponseEntity<CheckList> atualizarReserva(@PathVariable long id,
         @RequestBody AtualizarCheckListDTO checkListDTO) {
      LOGGER.info("CheckList de id {} alterado com sucesso!", id);

      return ResponseEntity.ok().body(checkListService.alterarCheckList(checkListDTO, id));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<String> deletarCheckList(@PathVariable long id) {
      try {
         checkListService.deletarCheckList(id);
         LOGGER.info("CheckList de id {} deletado do banco de dados!", id);
      } catch (Exception e) {
         LOGGER.error("Não foi possível excluir o checklist {} do banco de dados", id);
         return new ResponseEntity<>("Não foi possível excluir o checklist!", HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<String>("CheckList deletado do banco de dados com sucesso!", HttpStatus.OK);
   }
}