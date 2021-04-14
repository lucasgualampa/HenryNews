package com.henry.base.service;

import com.henry.base.model.Writer;
import com.henry.base.repository.WriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class WriterService {

    @Autowired
    private WriterRepository writerRepository;

    // get all the writers across autowired
    public List<Writer> getWriters() {
        return writerRepository.findAll();
    }

    // get a writer by id
    public Writer getWriter(Integer id){
        return writerRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    // set a writer
    public Writer addWriter(Writer writer){
        return writerRepository.save(writer);
    }

    // delete a writer by id
    public void deleteWriterById(Integer id){
        writerRepository.deleteById(id);
    }

    // edit a writer
    public Writer editWriter(Writer writer){
        Writer myWriter = writerRepository.findById(writer.getId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        Writer editedWriter = new Writer();
        editedWriter.setId(writer.getId());
        if (writer.getName() != null){
            editedWriter.setName(writer.getName());
        } else {
            editedWriter.setName(myWriter.getName());
        }
        if (writer.getSurname() != null){
            editedWriter.setSurname(writer.getSurname());
        } else {
            editedWriter.setSurname(myWriter.getSurname());
        }
        if (writer.getDni() != null) {
            editedWriter.setDni(writer.getDni());
        } else {
            editedWriter.setDni(writer.getDni());
        }

        return writerRepository.save(editedWriter);
    }

}
