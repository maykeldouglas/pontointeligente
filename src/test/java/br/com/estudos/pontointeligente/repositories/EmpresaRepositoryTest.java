package br.com.estudos.pontointeligente.repositories;

import br.com.estudos.pontointeligente.entities.Empresa;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest {

    @Autowired
    private EmpresaRepository empresaRepository;

    private static String CNPJ = "12345678000100";

    @BeforeEach
    public void setUp() throws Exception{
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Empresa Teste Repo");
        empresa.setCnpj(CNPJ);
        this.empresaRepository.save(empresa);
    }

    @AfterEach
    public void tearDown(){
        this.empresaRepository.deleteAll();
    }


    @Test
    public void buscarPorCnpj(){
        Empresa empresa = this.empresaRepository.findByCnpj(CNPJ);
        Assertions.assertEquals(CNPJ, empresa.getCnpj());
    }

}
