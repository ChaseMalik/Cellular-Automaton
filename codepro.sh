#!/bin/sh
# ***** Local Environment Variables ***********

# the eclipse home
ECLIPSEHOME="/opt/eclipse"

# get path to equinox jar inside ECLIPSEHOME folder
EQUINOXJAR=`ls $ECLIPSEHOME/plugins/org.eclipse.equinox.launcher_*.jar`

# The location of your workspace (does not need to exist)
WORKSPACE="/tmp/eclipse/codepro/cellsociety_team02"

# ****************************************************
java -jar $EQUINOXJAR -clean -noupdate -application org.eclipse.ant.core.antRunner -data $WORKSPACE -verbose -file CodePro.xml #> headless_out.txt 2>&1


