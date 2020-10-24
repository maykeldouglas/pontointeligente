package br.com.estudos.pontointeligente.repositories;

import br.com.estudos.pontointeligente.entities.Empresa;
import br.com.estudos.pontointeligente.entities.Funcionario;
import br.com.estudos.pontointeligente.entities.Lancamento;
import br.com.estudos.pontointeligente.enums.PerfilEnum;
import br.com.estudos.pontointeligente.enums.TipoEnum;
import br.com.estudos.pontointeligente.utils.PasswordUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private FuncionarioRepository funcionarioReposiFuncitory;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    private static String CNPJ = "12345678000100";

    private static String EMAIL = "funcionario@email.com";
    private static String CPF = "12345678912";

    private Long funcionarioId;

    @BeforeEach
    public void setUp() throws Exception{
        Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
        Funcionario funcionario = this.funcionarioReposiFuncitory.save(obterDadosFuncionario(empresa));
        this.funcionarioId = funcionario.getId();

        this.lancamentoRepository.save(obterDadosLancamento(funcionario));
        this.lancamentoRepository.save(obterDadosLancamento(funcionario));
    }

    @AfterEach
    public void tearDown(){
        this.empresaRepository.deleteAll();
    }

    @Test
    public void buscarLancamentosPorFuncionarioId(){
        List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId);
        Assertions.assertEquals(2, lancamentos.size());
    }

    @Test
    public void buscarLancamentosPorFuncionarioIdPaginado(){
        PageRequest pageRequest = PageRequest.of(0,10);
        Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
        Assertions.assertEquals(2, lancamentos.getTotalElements());
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

    public Lancamento obterDadosLancamento(Funcionario funcionario){
        Lancamento lancamento = new Lancamento();
        lancamento.setData(new Date());
        lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
        lancamento.setFuncionario(funcionario);
        return lancamento;
    }

}
