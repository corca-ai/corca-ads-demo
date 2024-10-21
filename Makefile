GREEN = \033[0;32m

set-permissions:
	@echo "$(GREEN)Setting permissions for run-demo.sh..."
	@chmod +x docker/run-demo.sh

run-demo: set-permissions
	@echo "$(GREEN)Running demo..."
	@docker/run-demo.sh

clear:
	@echo "$(GREEN)Clearing project-related Docker resources..."
	@docker-compose -f docker/docker-compose.yml down --rmi local
