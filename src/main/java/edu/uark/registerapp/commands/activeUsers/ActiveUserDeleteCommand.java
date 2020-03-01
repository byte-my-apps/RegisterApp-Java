package edu.uark.registerapp.commands.activeUsers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;

@Service
public class ActiveUserDeleteCommand implements VoidCommandInterface {
	@Override
	public void execute() {
		final Optional<ActiveUserEntity> activeUserEntity =
			this.activeUserRepository.findBySessionKey(this.request.getSession().getId());
			activeUserRepository.delete(activeUserEntity.get());
	}

	// Properties
	private HttpServletRequest request;

	@Autowired
	private ActiveUserRepository activeUserRepository;

	public ActiveUserDeleteCommand setRequest(HttpServletRequest request) {
		this.request = request;
		return this;
	}
}