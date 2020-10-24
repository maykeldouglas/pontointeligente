package br.com.estudos.pontointeligente.repositories;

import br.com.estudos.pontointeligente.entities.Empresa;
import br.com.estudos.pontointeligente.entities.Funcionario;
import br.com.estudos.pontointeligente.enums.PerfilEnum;
import br.com.estudos.pontointeligente.utils.PasswordUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.security.NoSuchAlgorithmException;

@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private FuncionarioRepository funcionarioReposiFuncitory;

    private static String CNPJ = "12345678000100";

    private static String EMAIL = "funcionario@email.com";
    private static String CPF = "12345678912";

    @BeforeEach
    public void setUp() throws Exception{
        Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
        this.funcionarioReposiFuncitory.save(obterDadosFuncionario(empresa));
    }

    @AfterEach
    public void tearDown(){
        this.funcionarioReposiFuncitory.deleteAll();
    }


    @Test
    public void buscarFuncionarioPorCpf(){
        Funcionario funcionario = this.funcionarioReposiFuncitory.findByCpf(CPF);
        Assertions.assertEquals(CPF, funcionario.getCpf());
    }

    @Test
    public void buscarFuncionarioPorEmail(){
        Funcionario funcionario = this.funcionarioReposiFuncitory.findByEmail(EMAIL);
        Assertions.assertEquals(EMAIL, funcionario.getEmail());
    }

    @Test
    public void buscarFuncionarioPorCpfOuEmail(){
        Funcionario funcionario = this.funcionarioReposiFuncitory.findByCpfOrEmail(CPF,EMAIL);
        Assertions.assertNotNull(funcionario);
    }


    @Test
    public void buscarFuncionarioPorCpfOuEmailInvalido(){
        Funcionario funcionario = this.funcionarioReposiFuncitory.findByCpfOrEmail(CPF,"invalido@email.com");
        Assertions.assertNotNull(funcionario);
    }

    @Test
    public void buscarFuncionarioPorCpfInvalidoOuEmail(){
        Funcionario funcionario = this.funcionarioReposiFuncitory.findByCpfOrEmail("99999999999",EMAIL);
        Assertions.assertNotNull(funcionario);
    }

    public Empresa obterDadosEmpresa(){
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Empresa Teste Repo");
        empresa.setCnpj(CNPJ);
        return empresa;
    }

    public Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Funcionario Teste Repo");
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
        funcionario.setCpf(CPF);
        funcionario.setEmail(EMAIL);
        funcionario.setEmpresa(empresa);
        return funcionario;
    }

}
