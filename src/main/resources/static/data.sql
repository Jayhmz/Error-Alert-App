INSERT INTO app_users (staff_id, firstname, lastname, email, role, password, isDisabled)
VALUES ('19623', 'James', 'Damilare', 'j@admin.com', 'ADMIN', 'hashed_password', 0);


INSERT INTO fcmb.clusters (cluster_name, created_by, updated_by, created_at) VALUES ('APAPA', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.clusters (cluster_name, created_by, updated_by, created_at) VALUES ('LAGOS ISLAND', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.clusters (cluster_name, created_by, updated_by, created_at) VALUES ('MAINLAND 1', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.clusters (cluster_name, created_by, updated_by, created_at) VALUES ('MAINLAND 2', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.clusters (cluster_name, created_by, updated_by, created_at) VALUES ('RIVERS/BAYELSA', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.clusters (cluster_name, created_by, updated_by, created_at) VALUES ('SOUTHWEST 1', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.clusters (cluster_name, created_by, updated_by, created_at) VALUES ('SOUTHWEST 2', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.clusters (cluster_name, created_by, updated_by, created_at) VALUES ('NORTH CENTRAL', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.clusters (cluster_name, created_by, updated_by, created_at) VALUES ('NORTH EAST', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.clusters (cluster_name, created_by, updated_by, created_at) VALUES ('NORTH WEST', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.clusters (cluster_name, created_by, updated_by, created_at) VALUES ('EDO-DELTA', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.clusters (cluster_name, created_by, updated_by, created_at) VALUES ('ENUGU', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.clusters (cluster_name, created_by, updated_by, created_at) VALUES ('ABUJA 1', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.clusters (cluster_name, created_by, updated_by, created_at) VALUES ('ABUJA 2', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.clusters (cluster_name, created_by, updated_by, created_at) VALUES ('CALABAR/OWERRI', '19623', '19623', CURRENT_TIMESTAMP);

INSERT INTO fcmb.branches (cluster_id, branch, sol_id, created_by, updated_by, created_at) VALUES ('APAPA', 'AP Branch 1', '056', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.branches (cluster_id, branch, sol_id, created_by, updated_by, created_at) VALUES ('APAPA', 'AP Branch 2', '154', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.branches (cluster_id, branch, sol_id, created_by, updated_by, created_at) VALUES ('APAPA', 'AP Branch 3', '142', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.branches (cluster_id, branch, sol_id, created_by, updated_by, created_at) VALUES ('APAPA', 'AP Branch 4', '006', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.branches (cluster_id, branch, sol_id, created_by, updated_by, created_at) VALUES ('LAGOS ISLAND', 'LI Branch 1', '175', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.branches (cluster_id, branch, sol_id, created_by, updated_by, created_at) VALUES ('LAGOS ISLAND', 'LI Branch 2', '280', '19623', '19623', CURRENT_TIMESTAMP);
INSERT INTO fcmb.branches (cluster_id, branch, sol_id, created_by, updated_by, created_at) VALUES ('LAGOS ISLAND', 'LI Branch 3', '034', '19623', '19623', CURRENT_TIMESTAMP);
