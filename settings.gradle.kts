rootProject.name = "kibo-pgar-lib"
include("src:main:java")
findProject(":src:main:java")?.name = "java"
