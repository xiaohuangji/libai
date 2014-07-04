#/bin/sh

PROJECT_NAME=dj-wika-mcp/
RUNNER_HOME=/home/chengwei/workspace/

[ $# -lt 1 ] && echo "Usage: run.sh mainclass" && exit 1

######run######
MCS_HOME=${RUNNER_HOME}${PROJECT_NAME}target/${PROJECT_NAME}/
CP=${MCS_HOME}WEB-INF/classes

for P in ${MCS_HOME}WEB-INF/lib/*.jar; do
    CP+=":"${P}
done

#echo $CP

echo "###### params ######"
echo $*
echo "###### start ######"

/opt/java/bin/java -cp $CP $* 
