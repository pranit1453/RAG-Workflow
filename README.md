# RAG-Workflow

docker run -d --name qdrant -p 6333:6333 -p 6334:6334 -e API_KEY="naive-rag@v1" -v qdrant_storage:/qdrant/storage
qdrant/qdrant:latest

docker pull qdrant/qdrant:latest