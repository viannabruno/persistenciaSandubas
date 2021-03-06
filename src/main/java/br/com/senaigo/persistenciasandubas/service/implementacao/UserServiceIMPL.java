package br.com.senaigo.persistenciasandubas.service.implementacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senaigo.persistenciasandubas.model.User;
import br.com.senaigo.persistenciasandubas.repository.UserDAO;
import br.com.senaigo.persistenciasandubas.repository.hql.GenercicDAO;
import br.com.senaigo.persistenciasandubas.service.UserService;
import br.com.senaigo.persistenciasandubas.util.StringUtil;
import lombok.Getter;

@Getter
@Service
public class UserServiceIMPL implements UserService{
	
	@Autowired
	private UserDAO persistencia;
	
	@Autowired
	private GenercicDAO genericDAO;

	@Override
	public List<User> findAll() {
		return persistencia.findAll();
	}

	@Override
	public User findById(String id) {
		return persistencia.findById(new Long(id)).get();
	}

	@Override
	public User save(User objeto) throws Exception {
		try {
			validarUsuario(objeto);
			return persistencia.save(objeto);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Boolean deleteById(String id) {
		try {
			persistencia.deleteById(new Long(id));
			return Boolean.TRUE;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Boolean existsByField(String fieldName, String value) throws Exception {
		try {
			Boolean objeto = genericDAO.existsByField(User.class, fieldName, value);
			return objeto;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public User findByField(String field, String value) {
		try {
			User objeto = genericDAO.findByField(User.class, field, value);
			return objeto;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public User login(String login, String senha) throws Exception {
		try {
			User objeto = persistencia.findByLoginAndSenha(login, senha);
			if(objeto == null) {
				throw new Exception("Usuário ou senha incorretos");
			}
			return objeto;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void validarUsuario(User objeto) throws Exception {
		if(StringUtil.isNullOrEmpity(objeto.getCpf())) {
			throw new Exception("CPF deve ser informado.");
		}
		if(StringUtil.isNullOrEmpity(objeto.getEmail())) {
			throw new Exception("E-mail deve ser informado.");
		}
		if(StringUtil.isNullOrEmpity(objeto.getLogin())) {
			throw new Exception("Login deve ser informado.");
		}
		if(StringUtil.isNullOrEmpity(objeto.getNome())) {
			throw new Exception("Nome deve ser informado.");
		}
		if(StringUtil.isNullOrEmpity(objeto.getSenha())) {
			throw new Exception("Senha deve ser informada.");
		}
		if(objeto.getId() == null || objeto.getId() <= 0) {
			User user = findByField("login", objeto.getLogin());
			if(user != null) {
				throw new Exception("Login ja se encontra em uso.");
			}
		}
		else {
			User userLogin = findByField("login", objeto.getLogin());
			User userId = findById(objeto.getId() + "");
			if(userLogin != null && !userLogin.getId().equals(userId.getId())) {
				throw new Exception("Login ja se encontra em uso.");
			}
		}
	}
}
