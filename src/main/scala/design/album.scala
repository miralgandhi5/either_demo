package design

import design.asset.checkerImp

import scala.concurrent.ExecutionContext

class album(implicit val ec: ExecutionContext) extends asset with checkerImp
